import { useMutation } from "@tanstack/react-query";
import { authLogin } from "../../../api/authLogin";
import { useAuthContext } from "../../../context/AuthContext";
import { schemaLogin, type LoginForm } from "../../../schemas/schemaLogin";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";

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
            <section>
                <div>

                </div>
                <div>
                    {isPending && (
                        <div>
                            <h2>Bem Vindo</h2>
                        </div>
                    )}
                    <form onSubmit={handleSubmit(onSubmit)}>
                        <div>
                            <input
                                type="email"
                                placeholder="Email"
                                {...register("email")}
                            />
                            {errors.email && (
                                <span>{errors.email.message}</span>
                            )}
                        </div>

                        <div>
                            <input
                                type="password"
                                placeholder="Senha"
                                {...register("password")}
                            />
                            {errors.password && (
                                <span>{errors.password.message}</span>
                            )}
                        </div>
                        <button type="submit">Entrar</button>
                    </form>
                </div>
            </section>
        </>
    );
}