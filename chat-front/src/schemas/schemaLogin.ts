import {z} from "zod"

export const schemaLogin = z.object({
    email: z.email('email inválido'),
    password: z.string().min(8)
        .regex(
            /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&.#_-])[A-Za-z\d@$!%*?&.#_-]{8,}$/,
            'A senha deve conter letra maiúsculas, minúsculas, números e @#!*'
        )
})

export type LoginForm = z.infer<typeof schemaLogin>