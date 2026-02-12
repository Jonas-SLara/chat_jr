import { useState } from "react";
import { useNavigate } from "react-router-dom";
import type { LoginRequest, LoginResponse, UserRequest } from "../../types/auth";
import { api } from "../../services/api";

export default function Auth() {
  const navigate = useNavigate();

  const [formLogin, setFormLogin] = useState<LoginRequest>({
    email: "",
    password: "",
  });

  async function handleLogin(e: React.SubmitEvent<HTMLFormElement>) {
    e.preventDefault();

    try {
      const { data } = await api.post<LoginResponse>("/auth/login", formLogin);

      localStorage.setItem("token", data.token);

      navigate("/dashboard");
    } catch {
      alert("Erro no login");
    }
  }

  const [formRegister, setFormRegister] = useState<UserRequest>({
    name: "",
    email: "",
    password: "",
  });

  async function handleRegister(e: React.SubmitEvent<HTMLFormElement>) {
    e.preventDefault();

    try {
      await api.post("/auth/cadastrar", formRegister);
      alert("Usuário criado!");
    } catch {
      alert("Erro ao cadastrar");
    }
  }

  return (
    <>
      <div>
        <form onSubmit={handleLogin}>
          <h2>Login</h2>

          <input
            placeholder="email"
            value={formLogin.email}
            onChange={(e) =>
              setFormLogin({ ...formLogin, email: e.target.value })
            }
          />

          <input
            type="password"
            placeholder="password"
            value={formLogin.password}
            onChange={(e) =>
              setFormLogin({ ...formLogin, password: e.target.value })
            }
          />

          <button type="submit">Entrar</button>

        </form>

      </div>
      <div>
        <form onSubmit={handleRegister}>
          <h2>Cadastro</h2>

          <input
            placeholder="nome"
            value={formRegister.name}
            onChange={(e) =>
              setFormRegister({ ...formRegister, name: e.target.value })
            }
          />

          <input
            placeholder="email"
            value={formRegister.email}
            onChange={(e) =>
              setFormRegister({ ...formRegister, email: e.target.value })
            }
          />

          <input
            type="password"
            placeholder="senha"
            value={formRegister.password}
            onChange={(e) =>
              setFormRegister({ ...formRegister, password: e.target.value })
            }
          />

          <button type="submit">Cadastrar</button>
        </form>
      </div>
    </>
  );
}