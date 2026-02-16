import { useMutation } from "@tanstack/react-query";
import { authLogin } from '@/api/authLogin';
import { useAuthContext } from '@/context/AuthContext';
import { schemaLogin, type LoginForm } from '@/schemas/schemaLogin';
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import styles from './Login.module.scss';

export default function Login() {
    const { login } = useAuthContext();

    const { mutateAsync, isPending } = useMutation({
        mutationFn: authLogin,
        onSuccess: (data) => {
            login(data.token, data.expiration);
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
            {isPending && (
                <div>
                    <h2>.....</h2>
                </div>
            )}
            {!isPending && (
                <div className={styles.form_container}>
                    <div className={styles.logo}>
                        <img src="/images/perfil.png" alt="perfil_logo" width={"200px"} />
                    </div>
                    <h2 className={styles.form_title}>Chat Compact Jr</h2>
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
                        <button type="submit" className={styles.btn_login}>Login</button>
                    </form>
                </div>
            )}
        </>
    );
}