import { useAuthContext } from "@/context/AuthContext";

export default function HeaderDash(){
    const {userAuthenticated, logout} = useAuthContext();

    return (
        <>
            <header>
                <div>
                    <img src="/images/perfil.png" width={"64px"}/>
                    <span>{userAuthenticated?.sub}</span>
                </div>
                <nav>

                </nav>
                <button onClick={logout}>Logout</button>
            </header>
        </>
    );
}