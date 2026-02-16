import { api } from "../services/api";
import type { LoginRequest, LoginResponse } from "../types/auth.types";

export const authLogin = async (data: LoginRequest) => {
    const response = await api.post<LoginResponse>("/auth/login", data);
    return response.data;
}