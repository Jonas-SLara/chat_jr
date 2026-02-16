import { createContext, useContext, useMemo, useState, type ReactNode } from "react";

import type { UserResponse } from "../types/user.types";

interface AuthContextType{
    token: string | null,
    userAuthenticated: UserResponse | null,
    expirationDate: Date | null,
    login: (token: string, expirationDate: Date) => void,
    logout: () => void,
    setUser: (user: UserResponse) => void;
    isAuth: boolean;
};

const TOKEN_KEY = import.meta.env.VITE_TOKEN_KEY;
const TOKEN_EXPIRATION_KEY = import.meta.env.VITE_TOKEN_EXPIRATION_KEY;
const USER_KEY = import.meta.env.VITE_USER_KEY;

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({children}: {children: ReactNode}){

    const [token, setToken] = useState<string | null>(localStorage.getItem(TOKEN_KEY));

    const [tokenExpiration, setTokenExpiration] = useState<Date | null>(() => {
        const data: string | null = localStorage.getItem(TOKEN_EXPIRATION_KEY);
        if (!data) return null;
        return new Date(data);
    });

    const [user, setUserState] = useState<UserResponse | null>(() => {
        const storedUser = localStorage.getItem(USER_KEY);
        return storedUser ? JSON.parse(storedUser) : null;
    });

    const login = (token: string, data: Date): void => {
        localStorage.setItem(TOKEN_KEY, token);
        const dateObj = data instanceof Date ? data : new Date(data);
        localStorage.setItem(TOKEN_EXPIRATION_KEY, dateObj.toISOString());
        setToken(token);
        setTokenExpiration(data);
    }

    const logout = (): void => {
        localStorage.clear();
        setToken(null);
        setTokenExpiration(null);
        setUserState(null);
    }

    function setUser(user: UserResponse) {
        localStorage.setItem(USER_KEY, JSON.stringify(user));
        setUserState(user);
    }

    const isAuth = useMemo(()=>{
        if (!token || !tokenExpiration) return false;
        const now = new Date();
        const isValid = now < new Date(tokenExpiration);
        return isValid;
    }, [token, tokenExpiration]);

    return (
        <AuthContext.Provider value={{
            token,
            isAuth,
            userAuthenticated: user,
            expirationDate: tokenExpiration,
            login,
            logout,
            setUser
        }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuthContext(){
    const context = useContext(AuthContext);
    if(!context){
        throw new Error("Contexto Da Aplicação Sendo Usado Fora Do Lugar"); 
    }
    return context;
}
