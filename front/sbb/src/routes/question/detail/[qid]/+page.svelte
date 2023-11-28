<script>
	import { page } from "$app/stores";
	import {onMount} from "svelte";
	import {toastWarning} from "../../../../app.js";

	let questionId =  $state({});
	let questionData = $state({});
	let answerCount = $state({});

	let loginCheck = $state({});
	let loginUsername = $state({});

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
		if (response) {
			const data = await response.json();
			loginCheck = data.result;
			loginUsername = data.username;
		}
	}

	async function getQuestion() {
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

	async function moveToModifyQuestionPage() {
		await memberCheck();
		if (loginCheck) {
			window.location.href = `/question/modify/${questionId}`;
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function removeQuestion() {
		await memberCheck();
		if (loginCheck) {
			await fetch(`/sbb/question/remove/${questionId}`, {
				method: 'POST',
			});
			window.location.href = `/question`;
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function removeAnswer(answerId) {
		await memberCheck();
		if (loginCheck) {
			await fetch(`/sbb/answer/remove/${answerId}`, {
				method: 'POST',
			});
			window.location.reload();
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function modifyHandleSubmit(event, answerId) {
		event.preventDefault();
		await memberCheck();
		if (loginCheck) {
			const formData = new FormData(event.target);
			const response = await fetch(`/sbb/answer/modify/${answerId}`, {
				method: 'POST',
				body: formData,
			});
			console.log(response);

			if (!response.ok) {
				const errorData = await response.json();
				if (errorData.content) {
					toastWarning(errorData.content);
					return;
				}
			}
			window.location.href=`/question/detail/${questionId}`;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function writeHandleSubmit(event) {
		event.preventDefault();
		await memberCheck();
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
		await memberCheck();
		await getQuestion();
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
					<div class="flex flex-row justify-between mt-5">
						<div class="flex flex-row justify-start">
							{#if questionData.author}
								{#if loginUsername === questionData.author.username}
									<a class="btn btn-ghost border-white mr-3" on:click={moveToModifyQuestionPage}>수정</a>
									<a class="btn btn-ghost border-white" on:click={removeQuestion}>삭제</a>
								{/if}
							{/if}
						</div>
						<div class="flex flex-col">
							<div class="flex flex-row justify-end">
								<div class="badge badge-primary badge-outline mb-1.5">
									{#if questionData.author}
										{questionData.author.username}
									{/if}
								</div>
							</div>
							<div class="badge badge-primary badge-outline text-start">
								{questionData.createDate}
							</div>
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
							<div class="flex flex-row justify-between mt-5">
								<div class="flex flex-row justify-start">
									{#if answer.author}
										{#if loginUsername === answer.author.username}
											<a href="#edit_modal" class="btn btn-ghost border-white mr-3">수정</a>
											<div class="modal" role="dialog" id="edit_modal">
												<div class="modal-box">
													<h3 class="font-bold text-lg">답변 수정</h3>
													<form class="w-full" on:submit|preventDefault={(event) => modifyHandleSubmit(event, answer.id)} method="post">
														<div class="flex flex-row items-stretch">
															<textarea class="textarea textarea-primary m-5 w-full resize-none" name="content" id="content" value="{answer.content}" rows="3"></textarea>
															<button type="submit" class="btn btn-primary m-5 h-24">수정</button>
														</div>
													</form>
													<div class="modal-action">
														<a href="#" class="btn">닫기</a>
													</div>
												</div>
											</div>
											<a class="btn btn-ghost border-white" on:click|preventDefault={() => removeAnswer(answer.id)}>삭제</a>
										{/if}
									{/if}
								</div>
								<div class="flex flex-col">
									<div class="flex flex-row justify-end">
										<div class="badge badge-primary badge-outline mb-1.5">
											{#if answer.author}
												{answer.author.username}
											{/if}
										</div>
									</div>
									<div class="badge badge-primary badge-outline text-start">
										{answer.createDate}
									</div>
								</div>
							</div>
						</div>
					</div>
				{/each}
			{/if}
		</div>


		<div class="flex content-center flex-wrap w-full">
			<form class="w-full" on:submit={writeHandleSubmit} method="post">
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
