<script>
  import {toastWarning} from "../../../app.js";
  import Cookies from 'js-cookie';

  async function handleSubmit(event) {
    event.preventDefault();
    const formData = new FormData(event.target);

    if (formData) {
      const response = await fetch(`/sbb/member/login`, {
        method: 'POST',
        body: formData,
      });

      if (!response.ok) {
        const errorData = await response.json();
        console.log(errorData);
        if (errorData.username) {
          toastWarning(errorData.username);
          return;
        }

        if (errorData.password) {
          toastWarning(errorData.password);
          return;
        }

        if (errorData.exception) {
          toastWarning(errorData.exception);
          return;
        }
      }

      const data = await response.json();
      Cookies.set("token", data.token);
      window.history.back();
    }
  }

</script>

<svelte:head>
  <title>SBB</title>
  <meta name="description" content="SBB Question List" />
</svelte:head>

<section class="pl-10 pr-10">
  <div>
    <h2 class="text-3xl font-bold border-bottom py-2 m-5">회원 가입</h2>
    <form on:submit={handleSubmit} method="post">
      <div class="flex flex-col m-5">
        <label for="username" class="form-label">아이디</label>
        <input class="input input-bordered input-primary mt-3 max-w-full" name="username" id="username" type="text" placeholder="ID를 입력해주세요."/>
      </div>
      <div class="flex flex-col m-5">
        <label for="password" class="form-label">비밀번호</label>
        <input class="input input-bordered input-primary mt-3 max-w-full" name="password" id="password" type="password" placeholder="비밀번호를 입력해주세요."/>
      </div>
      <div class="flex flex-col m-5">
        <button type="submit" class="btn btn-primary mt-3">로그인</button>
      </div>
      <div class="flex flex-col m-5 items-center">
        <span class="text-center text-opacity-30"> ----- 처음 이용하시나요? ----- </span>
        <a class="btn btn-primary mt-3 w-96" href="/member/register">회원가입</a>
      </div>
    </form>
  </div>
</section>

<style>

</style>
