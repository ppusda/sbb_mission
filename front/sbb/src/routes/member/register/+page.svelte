<script>
  import {toastWarning, toastNotice} from "../../../app.js";

  async function handleSubmit(event) {
    event.preventDefault();
    const formData = new FormData(event.target);

    if (formData) {
      const response = await fetch(`/sbb/member/register`, {
        method: 'POST',
        body: formData,
      });

      if (!response.ok) {
        const errorData = await response.json();
        console.log(errorData);
        if (errorData.username) {
          toastWarning(errorData.username);
        }
        if (errorData.password) {
          toastWarning(errorData.password);
        }
        if (errorData.passwordCheck) {
          toastWarning(errorData.passwordCheck);
        }
        if (errorData.email) {
          toastWarning(errorData.email);
        }
        return;
      }

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
        <label for="password" class="form-label">비밀번호 확인</label>
        <input class="input input-bordered input-primary mt-3 max-w-full" name="passwordCheck" id="passwordCheck" type="password" placeholder="비밀번호를 입력해주세요."/>
      </div>
      <div class="flex flex-col m-5">
        <label for="password" class="form-label">이메일</label>
        <input class="input input-bordered input-primary mt-3 max-w-full" name="email" id="email" type="email" placeholder="이메일을 입력해주세요."/>
      </div>
      <div class="flex flex-col m-5">
        <button type="submit" class="btn btn-primary mt-3">회원가입</button>
      </div>
    </form>
  </div>
</section>

<style>

</style>
