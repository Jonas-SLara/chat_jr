import { Navigate, Outlet } from "react-router-dom";
import { useAuthContext } from "../context/AuthContext";

export default function AuthLayout(){
    const {isAuthenticated} = useAuthContext()

    if(isAuthenticated()){
        return <Navigate to="/app" replace />
    }
    return(
        <>
            <Outlet/>
        </>
    );
}