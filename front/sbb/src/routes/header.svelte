<script>
  import {toastNotice} from "../app.js";
  import {onMount} from "svelte";
  import Cookies from 'js-cookie';

  let loginCheck = $state({});

  async function fetchData() {
    const response = await fetch(`/sbb/member/check`);
    loginCheck = await response.json();
  }

  function logoutProcess() {
    logout();
    toastNotice("로그아웃 되었습니다.");
  }
  async function logout() {
    await fetch(`/sbb/member/logout`);
    Cookies.remove("token");
    window.location.reload();
  }

  onMount(async () => {
    await fetchData();
  });
</script>

<div class="sticky top-0 h-16 bg-base-200 navbar bg-base-100 z-10">
  <div class="navbar-start">
    <div class="dropdown">
      <label tabindex="0" class="btn btn-ghost btn-circle">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h7" /></svg>
      </label>
      <ul tabindex="0" class="menu menu-sm dropdown-content mt-3 z-[1] p-2 shadow bg-base-100 rounded-box w-52">
        <li><a href="/"><i class="fa-solid fa-house"></i>메인 페이지</a></li>
        <li><a href="/question"><i class="fa-solid fa-table-list"></i>질문 하기</a></li>
        <li><a href="https://github.com/ppusda/"><i class="fa-brands fa-github"></i>Git</a></li>
      </ul>
    </div>
  </div>
  <div class="navbar-center">
    <a class="btn btn-ghost text-xl" href="/">SBB project</a>
  </div>
  <div class="navbar-end">
    {#if !loginCheck}
        <a class="btn btn-ghost" href="/member/login">로그인</a>
      {:else}
      <a class="btn btn-ghost" on:click={logoutProcess}>로그아웃</a>
    {/if}


  </div>
</div>