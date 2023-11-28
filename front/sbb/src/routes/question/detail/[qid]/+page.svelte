<script>
	import { page } from "$app/stores";
	import {onMount} from "svelte";
	import {toastWarning} from "../../../../app.js";

	let questionId =  $state({});
	let questionData = $state({});
	let answerCount = $state({});

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

	async function memberCheck() {
		const response = await fetch(`/sbb/member/check`);
		let loginCheck = false;
		if (response) {
			loginCheck = await response.json();
		}
		return loginCheck;
	}

	async function fetchData() {
		const response = await fetch(`/sbb/question/detail/${questionId}`);
		questionData = await response.json();

		answerCount = questionData.answerList.length;

		if (questionData.createDate) {
			questionData.createDate = formatDate(questionData.createDate);
		}

		if (answerCount >= 1) {
			questionData.answerList.forEach(async (answer) => {
				answer.createDate = formatDate(answer.createDate);
			});
		}
	}

	async function handleSubmit(event) {
		event.preventDefault();
		const loginCheck = await memberCheck();
		if (loginCheck) {
			const formData = new FormData(event.target);
			const response = await fetch(`/sbb/answer/write/${questionId}`, {
				method: 'POST',
				body: formData,
			});

			if (!response.ok) {
				const errorData = await response.json();
				if (errorData.content) {
					toastWarning(errorData.content);
					return;
				}
			}
			window.location.reload();
		}
		toastWarning("로그인이 필요합니다.");
	}

	onMount(async () => {
		questionId = $page.params['qid'];
		await fetchData();
	});

</script>

<svelte:head>
	<title>SBB</title>
	<meta name="description" content="SBB Question" />
</svelte:head>

<section class="pl-10 pr-10">
	<div class="flex flex-col">
		<div class="flex flex-col content-start flex-wrap">
			<h2 class="text-3xl font-bold border-bottom py-2 m-5"> {questionData.subject} </h2>
			<div class="card bg-base-100 shadow-xl border m-5 w-7/12">
				<div class="card-body flex flex-col">
					<h2 class="card-title">{questionData.subject} </h2>
					<p>{questionData.content}</p>
					<div class="flex flex-row justify-end">
						<div class="badge badge-primary badge-outline">
							{questionData.createDate}
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="flex flex-col content-end flex-wrap">
			<h5 class="text-1xl m-5">{answerCount} 개의 답변이 있습니다.</h5>
			{#if questionData && questionData.answerList}
				{#each questionData.answerList as answer}
					<div class="card bg-base-100 shadow-xl border m-5 w-7/12">
						<div class="card-body">
							<p>{answer.content}</p>
							<div class="flex flex-row justify-end">
								<div class="badge badge-primary badge-outline">
									{answer.createDate}
								</div>
							</div>
						</div>
					</div>
				{/each}
			{/if}
		</div>


		<div class="flex content-center flex-wrap w-full">
			<form class="w-full" on:submit={handleSubmit} method="post">
				<div class="flex flex-row m-5 items-stretch">
					<textarea class="textarea textarea-primary m-5 w-full resize-none" name="content" id="content" rows="3"></textarea>
					<button type="submit" class="btn btn-primary m-5 h-24">답변 등록</button>
				</div>
			</form>
		</div>
	</div>
</section>

<style>

</style>
