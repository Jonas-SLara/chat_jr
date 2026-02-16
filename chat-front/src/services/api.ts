import axios from "axios";

const TOKEY_KEY = import.meta.env.VITE_TOKEN_KEY

export const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    timeout: 10000,
    headers: {},
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem(TOKEY_KEY);

    if(token){
        config.headers.Authorization = `Bearer ${token}`
    }

    return config;
});

api.interceptors.response.use(
  response => response,
  error => {
    if (error.response?.status === 401) {
      localStorage.clear();
      window.location.href = "/";
    }
    return Promise.reject(error);
  }
);
