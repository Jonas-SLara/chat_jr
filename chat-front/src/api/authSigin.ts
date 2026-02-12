import { api } from "../services/api";
import type { UserRequest, UserResponse } from "../types/user.types";

export const authSigin = async (data: UserRequest) => {
    await api.post<UserResponse>("/auth/cadastrar", data);
}