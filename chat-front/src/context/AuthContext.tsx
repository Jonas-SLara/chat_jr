import { createContext, useContext, useEffect, useMemo, useState, type ReactNode } from "react";
import { jwtDecode } from "jwt-decode";
import type { tokenPayload } from "@/types/tokenPayload.types";

interface AuthContextType{
    token: string | null,
    userAuthenticated: tokenPayload | null,
    expirationDate: Date | null,
    login: (token: string, expirationDate: Date) => void,
    logout: () => void,
    isAuth: boolean;
};

const TOKEN_KEY = import.meta.env.VITE_TOKEN_KEY;
const TOKEN_EXPIRATION_KEY = import.meta.env.VITE_TOKEN_EXPIRATION_KEY;

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({children}: {children: ReactNode}){

    const [token, setToken] = useState<string | null>(localStorage.getItem(TOKEN_KEY));

    const [tokenExpiration, setTokenExpiration] = useState<Date | null>(() => {
        const data: string | null = localStorage.getItem(TOKEN_EXPIRATION_KEY);
        if (!data) return null;
        return new Date(data);
    });

    const [user, setUser] = useState<tokenPayload | null>(null);

    const logout = (): void => {
        localStorage.clear();
        setToken(null);
        setTokenExpiration(null);
        setUser(null);
    }

    const decodeAndSetUser = (token: string) => {
        try {
            const decoded = jwtDecode<tokenPayload>(token);
            setUser(decoded);
            console.log('usuario buscado pelo payload');
        } catch (e) {
            console.error('Erro ao decodificar token', e);
            logout(); 
        }
    };

     const login = (token: string, data: Date): void => {
        localStorage.setItem(TOKEN_KEY, token);
        const dateObj = data instanceof Date ? data : new Date(data);
        localStorage.setItem(TOKEN_EXPIRATION_KEY, dateObj.toISOString());
        
        setToken(token);
        setTokenExpiration(data);
        decodeAndSetUser(token);
    }

    const isAuth = useMemo(()=>{
        if (!token || !tokenExpiration) return false;
        const now = new Date();
        const isValid = now < new Date(tokenExpiration);
        return isValid;
    }, [token, tokenExpiration]);

    // Efeito para reidratar o usuário se o token existir mas o estado 'user' for null
    // Isso acontece no F5 ou quando o usuário abre uma nova aba
    useEffect(() => {
        if (token && !user && tokenExpiration && new Date(tokenExpiration) > new Date()) {
            decodeAndSetUser(token);
        }
    }, [token, user, tokenExpiration]);

    return (
        <AuthContext.Provider value={{
            token,
            isAuth,
            userAuthenticated: user,
            expirationDate: tokenExpiration,
            login,
            logout
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
