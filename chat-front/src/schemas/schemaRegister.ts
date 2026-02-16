import {z} from "zod";

export const schemaRegister = z.object({
    name: z.string().min(3, 'Nome muito curto'),
    email: z.email('Email inválido'),
    password: z.string().min(8, 'Mínimo 8 caracteres')
        .regex(/[A-Z]/, 'Falta letra maiúscula')
        .regex(/[a-z]/, 'Falta letra minúscula')
        .regex(/[\d]/, 'Falta um número')
        .regex(/[@$!%*?&.#_-]/, 'Falta caractere especial')
});

export type RegisterForm = z.infer<typeof schemaRegister>