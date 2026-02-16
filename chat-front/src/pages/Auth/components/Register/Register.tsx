import { authSigin } from "@/api/authSigin";
import { schemaRegister, type RegisterForm } from "@/schemas/schemaRegister";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useForm } from "react-hook-form";
import styles from '../Login/Login.module.scss';

export default function Register() {

    const { data, mutateAsync, isPending, error } = useMutation({
        mutationFn: authSigin,
        onSuccess: () => {
            alert(`Sucesso ao Cadastrar ${data?.name} ${data?.id}`);
        },
        onError: () => {
            alert(`Erro ao Cadastrar ${error?.message} : ${error?.cause}`);
        }
    })

    const { handleSubmit, register, formState: { errors } } = useForm<RegisterForm>({
        resolver: zodResolver(schemaRegister)
    })

    const onSubmit = async (data: RegisterForm) => {
        await mutateAsync(data)
    }

    return (
        <>
            <div className={styles.form_container}>
                <h2 className={styles.form_title}>Criar Conta</h2>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className={styles.input_container}>
                        <label>Nome:</label>
                        <input {...register("name")} placeholder="Seu nome" type="text" />
                        {errors.name && <span className={styles.span_box_input}>{errors.name.message}</span>}
                    </div>

                    <div className={styles.input_container}>
                        <label>Email:</label>
                        <input {...register("email")} placeholder="Email" type="email" />
                        {errors.email && <span className={styles.span_box_input}>{errors.email.message}</span>}
                    </div>

                    <div className={styles.input_container}>
                        <label>Senha:</label>
                        <input {...register("password")} placeholder="Senha" type="password" />
                        {errors.password && <span className={styles.span_box_input}>{errors.password.message}</span>}
                    </div>
                    <button type="submit" className={styles.btn_login} disabled={isPending}>
                        {isPending ? "Cadastrando..." : "Confirmar Cadastro"}
                    </button>
                </form>
            </div>
        </>
    );
}