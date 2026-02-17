import Login from '@/pages/Auth/components/Login/Login';
import style from './Auth.module.scss'
import Register from './components/Register/Register';
import { useState } from 'react';

export default function Auth() {

  const [isLogin, setIsLogin] = useState<boolean>(true);

  return (
    <main className={style.auth_container}>
      <div className={style.toglle_link} onClick={() => { setIsLogin(!isLogin) }}>
        <span>
          {!isLogin
            ? "Já Tem Uma Conta? Fazer Login"
            : "Não Possuí Uma Conta: Fazer Uma"}
        </span>
      </div>
      {isLogin ? (
        <Login key={"login"}/>
      ) : (
        <Register key={"register"} />
      )}
    </main>
  );
}