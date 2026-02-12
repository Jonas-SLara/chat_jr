import { api } from "../services/api";
import type { LoginRequest, LoginResponse } from "../types/auth.types";

export const login = async (data: LoginRequest) => {
    await api.post<LoginResponse>("/auth/login", data);
}