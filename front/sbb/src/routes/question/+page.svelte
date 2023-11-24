<script>
	let questionListData = $state([]);

	function formatDate(datePhrase) {
		const date = new Date(datePhrase);
		return date.toLocaleDateString('ko-KR', {
			year: 'numeric',
			month: '2-digit',
			day: '2-digit',
		});
	}

	async function fetchData() {
		const response = await fetch(`/sbb/question/list`);
		questionListData = await response.json();

		if (questionListData) {
			questionListData.forEach(async (question) => {
				question.createDate = formatDate(question.createDate);
			});
		}
	}

	fetchData();
</script>

<svelte:head>
	<title>SBB</title>
	<meta name="description" content="SBB Question List" />
</svelte:head>

<section>
	<div class="container w-screen">
		<h2 class="text-2xl font-bold border-bottom py-2 m-5"> 질문 게시판</h2>
	</div>
	<div class="overflow-x-auto m-5">
		<table class="table">
			<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성 일시</th>
			</tr>
			</thead>
			<tbody>
			{#each questionListData as data}
				<tr class="hover">
					<td> {data.id}</td>
					<td> <a href={`/question/detail/${data.id}`} >{data.subject}</a> </td>
					<td> {data.createDate} </td>
				</tr>
			{/each}
			</tbody>
		</table>
	</div>
</section>

<style>

</style>
