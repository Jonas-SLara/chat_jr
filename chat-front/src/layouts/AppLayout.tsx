import { Navigate, Outlet } from "react-router-dom";
import { useAuthContext } from "../context/AuthContext";

export default function AppLayout(){
    const {isAuth} = useAuthContext()

    if(!isAuth){
        console.log('log: não autenticado');
        return <Navigate to="/" replace />
    }

    return(
        <>
            <Outlet/>
        </>
    );
}