<script>
	import {toastWarning} from "../../app.js";
	import {onMount} from "svelte";

	let currentPage = $state({});
	let totalItems = $state({});
	let totalPages = $state({});
	let questionListData = $state([]);

	const changePage = (page) => {
		currentPage = page;
		getQuestionList();
	}

	const PrevPage = () => {
		if (currentPage > 0) {
			currentPage --;
			getQuestionList();
		}
	}

	const NextPage = () => {
		if (currentPage < totalPages-1) {
			currentPage ++;
			getQuestionList();
		}
	}

	function formatDate(datePhrase) {
		const date = new Date(datePhrase);
		return date.toLocaleDateString('ko-KR', {
			year: 'numeric',
			month: '2-digit',
			day: '2-digit',
			hour: '2-digit',
			minute: '2-digit',
		});
	}

	async function moveToWriteQuestionPage() {
		const loginCheck = await memberCheck();
		if (loginCheck) {
			window.location.href = '/question/write';
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function memberCheck() {
		const response = await fetch(`/sbb/member/check`);
		let loginCheck = false;
		if (response) {
			loginCheck = await response.json();
		}
		return loginCheck;
	}

	async function getQuestionList() {
		const response = await fetch(`/sbb/question/list?page=${currentPage}`);
		const jsonResponse = await response.json();
		if (jsonResponse) {
			questionListData = jsonResponse.content;
			totalItems = jsonResponse.totalElements;
			totalPages = jsonResponse.totalPages;

			questionListData.forEach(async (question) => {
				question.createDate = formatDate(question.createDate);
			});
		}
	}


	onMount(async () => {
		currentPage = 0;
		await getQuestionList();
	});

</script>

<svelte:head>
	<title>SBB</title>
	<meta name="description" content="SBB Question List" />
</svelte:head>

<section class="pl-10 pr-10">
	<div class="flex flex-row items-center justify-between">
		<div>
			<h2 class="text-3xl font-bold border-bottom py-2 m-5">질문 게시판</h2>
		</div>
		<div>
			<a class="btn btn-primary py-2 m-5" on:click={moveToWriteQuestionPage}>질문 등록</a>
		</div>
	</div>
	<div class="overflow-x-auto m-5">
		<table class="table">
			<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성 일시</th>
			</tr>
			</thead>
			<tbody>
			{#each questionListData as data}
				<tr class="hover">
					<td>{data.id}</td>
					<td>
						<a href={`/question/detail/${data.id}`} >{data.subject}</a>
						{#if data.answerList.length !== 0}
							<span class="text-primary ml-1">{data.answerList.length}</span>
						{/if}
					</td>
					<td>{data.author.username}</td>
					<td> {data.createDate} </td>
				</tr>
			{/each}
			</tbody>
		</table>
	</div>

	<div class="join flex justify-center mt-5">
		{#if totalPages !== 0}
			<input class="join-item btn btn-square" value="이전" on:click={() => PrevPage()}/>
		{/if}
		{#each Array(totalPages) as _, idx}
			{#if idx <= 1 || idx >= totalPages - 2 || (idx >= currentPage - 1 && idx <= currentPage + 1)}
				{#if currentPage === idx}
					<input class="join-item btn btn-square" type="radio" name="options" aria-label="{idx+1}" checked/>
				{:else}
					<input class="join-item btn btn-square" type="radio" name="options" aria-label="{idx+1}" on:click={() => changePage(idx)}/>
				{/if}
			{:else if (idx === 2 && currentPage > 2) || (idx === totalPages - 3 && currentPage < totalPages - 3)}
				<button class="join-item btn btn-disabled">...</button>
			{/if}
		{/each}
		{#if totalPages !== 0}
			<input class="join-item btn btn-square" value="다음" on:click={() => NextPage()}/>
		{/if}
	</div>

</section>

<style>

</style>
