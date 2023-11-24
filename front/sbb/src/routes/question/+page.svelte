<script>
	let datas = $state([]);

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
				<td> <a href={`/question/detail/${data.id}`} >{data.subject}</a> </td>
				<td> {data.content} </td>
			</tr>
		{/each}
		</tbody>
	</table>
</section>

<style>

</style>
