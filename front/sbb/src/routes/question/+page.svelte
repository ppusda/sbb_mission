<script>
	// 사용자 입력을 위한 상태
	let username = $state('');

	// 리포지토리 목록을 저장할 상태
	let datas = $state([]);

	// 사용자 이름을 입력하고 '데이터 가져오기' 버튼을 누를 때 실행할 함수
	async function fetchData() {
		const response = await fetch(`/sbb/question/list`);
		datas = await response.json();
	}

	fetchData();
</script>

<svelte:head>
	<title>Test</title>
	<meta name="description" content="테스트 데이터 가져오기" />
</svelte:head>

<section>
	<table>
		<thead>
		<tr>
			<th>제목</th>
			<th>내용</th>
		</tr>
		</thead>
		<tbody>
		{#each datas as data}
			<tr>
				<td> {data.subject} </td>
				<td> {data.content} </td>
			</tr>
		{/each}
		</tbody>
	</table>
</section>

<style>

</style>
