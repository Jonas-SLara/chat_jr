import { useMutation } from "@tanstack/react-query";
import { authLogin } from '@/api/authLogin';
import { useAuthContext } from '@/context/AuthContext';
import { schemaLogin, type LoginForm } from '@/schemas/schemaLogin';
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import styles from './Login.module.scss';
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

export default function Login() {

    const { login, isAuth } = useAuthContext();
    const navigate = useNavigate();

    // ESCUTADOR DE AUTENTICAÇÃO:
    // Assim que o isAuth virar true no contexto, o navigate dispara sozinho.
    useEffect(() => {
        if (isAuth) {
            navigate('/app', { replace: true });
            console.log('mudou')
        }
    }, [isAuth, navigate]);

    const { mutateAsync, isPending } = useMutation({
        mutationFn: authLogin,
        onSuccess: (data) => {
            login(data.token, data.expiration);
            console.log('log de auth: ' + isAuth);
        },
        onError(error) {
            alert(error.cause + "\n" + error.message)
        },
    });

    const { register, handleSubmit, formState: { errors } } = useForm<LoginForm>({
        resolver: zodResolver(schemaLogin)
    });


    const onSubmit = async (data: LoginForm) => {
        await mutateAsync(data);
    }

    return (
        <>
            <div className={styles.form_container}>
                <div className={styles.logo}>
                    <img src="/images/perfil.png" alt="perfil_logo" width={"200px"} />
                </div>
                <h2 className={styles.form_title}>Chat Jr</h2>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className={styles.input_container}>
                        <label>Email: </label>
                        <input
                            type="email"
                            placeholder="Email"
                            {...register("email")}
                        />
                        {errors.email && (
                            <span className={styles.span_box_input}>{errors.email.message}</span>
                        )}
                    </div>

                    <div className={styles.input_container}>
                        <label>Senha: </label>
                        <input
                            type="password"
                            placeholder="Senha"
                            {...register("password")}
                        />
                        {errors.password && (
                            <span className={styles.span_box_input}>{errors.password.message}</span>
                        )}
                    </div>
                    <button type="submit" className={styles.btn_login} disabled={isPending}>
                        {isPending ? "..." : "Confirmar Login"}
                    </button>
                </form>
            </div>
        </>
    );
}