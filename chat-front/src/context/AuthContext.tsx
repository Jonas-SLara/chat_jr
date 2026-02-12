import { createContext, useContext, useState, type ReactNode } from "react";

import type { UserResponse } from "../types/user.types";

interface AuthContextType{
    token: string | null,
    userAuthenticated: UserResponse | null,
    expirationDate: Date | null,
    login: (token: string, expirationDate: Date) => void,
    logout: () => void,
    setUser: (user: UserResponse) => void;
    isAuthenticated: () => boolean;
};


const TOKEN_KEY = "token_data";
const TOKEN_EXPIRATION_KEY = "token_expiration_key";
const USER_KEY = "user_data";

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
        localStorage.setItem(TOKEN_EXPIRATION_KEY, data.toISOString());
        setToken(token);
        setTokenExpiration(data);
    }

    const logout = (): void => {
        localStorage.removeItem(TOKEN_KEY);
        localStorage.removeItem(TOKEN_EXPIRATION_KEY);
        setToken(null);
        setTokenExpiration(null);
        setUserState(null);
    }

    function setUser(user: UserResponse) {
        localStorage.setItem(USER_KEY, JSON.stringify(user));
        setUserState(user);
    }

    function isAuthenticated(): boolean {
        if (!token || !tokenExpiration || !user) return false;

        const now = new Date();
        return now < tokenExpiration;
    }

    return (
        <AuthContext.Provider value={{
            token,
            isAuthenticated,
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
