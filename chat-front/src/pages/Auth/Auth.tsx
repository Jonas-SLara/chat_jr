import Login from '@/pages/Auth/components/Login/Login';
import style from './Auth.module.scss'

export default function Auth() {
  

  return (
    <main className={style.auth_container}>
      <Login/>
    </main>
  );
}