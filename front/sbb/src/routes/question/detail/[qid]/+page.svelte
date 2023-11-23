<script>
	import { page } from "$app/stores";

	const questionId = $page.params['qid'];

	// 리포지토리 목록을 저장할 상태
	let datas = $state({});

	// 사용자 이름을 입력하고 '데이터 가져오기' 버튼을 누를 때 실행할 함수
	async function fetchData() {
		const response = await fetch(`/sbb/question/detail/${questionId}`);
		datas = await response.json();
	}

	fetchData();
</script>

<svelte:head>
	<title>Test</title>
	<meta name="description" content="테스트 데이터 상세보기" />
</svelte:head>

<section>
	<h1 class="text-4xl">{datas.subject}</h1>
	<p>{datas.content}</p>

</section>

<style>

</style>
