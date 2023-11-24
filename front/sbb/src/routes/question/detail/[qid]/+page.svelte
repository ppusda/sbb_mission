<script>
	import { page } from "$app/stores";

	const questionId = $page.params['qid'];

	let datas = $state({});
	let answerCount = $state({});

	async function fetchData() {
		const response = await fetch(`/sbb/question/detail/${questionId}`);
		datas = await response.json();

		answerCount = datas.answerList.length;
	}

	fetchData();

	async function handleSubmit(event) {
		event.preventDefault();
		const formData = new FormData(event.target);
		await fetch(`/sbb/answer/write/${datas.id}`, {
			method: 'POST',
			body: formData,
		});
		window.location.href = `/question/detail/${datas.id}`;
	}

</script>

<svelte:head>
	<title>Test</title>
	<meta name="description" content="테스트 데이터 상세보기" />
</svelte:head>

<section>
	<h1 class="text-4xl">{datas.subject}</h1>
	<p>{datas.content}</p>

	<h5>{answerCount} 개의 답변이 있습니다.</h5>
	<div>
		<ul>
			{#each datas.answerList as answer}
				<li> {answer.content} </li>
			{/each}
		</ul>
	</div>

	<form on:submit={handleSubmit} method="post">
		<textarea class="textarea textarea-primary mt-5" name="content" id="content" rows="10"></textarea>
		<button type="submit" class="btn btn-primary" value="답변등록">답변 등록</button>
	</form>

</section>

<style>

</style>
