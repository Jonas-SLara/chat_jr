import { Navigate, Outlet } from "react-router-dom";
import { useAuthContext } from "../context/AuthContext";

export default function AuthLayout(){
    const { isAuth }= useAuthContext()

    if(isAuth) {
        return <Navigate to="/app" replace />
    }
    return(
        <>
            <Outlet/>
        </>
    );
}