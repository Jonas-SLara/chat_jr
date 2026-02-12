import { Navigate, Outlet } from "react-router-dom";
import { useAuthContext } from "../context/AuthContext";

export default function AppLayout(){
    const {isAuthenticated} = useAuthContext()

    if(!isAuthenticated()){
        return <Navigate to="/" replace />
    }

    return(
        <>
            <Outlet/>
        </>
    );
}