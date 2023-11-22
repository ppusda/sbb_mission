<script>
	$effect(() => {
		window.$('body').append("안녕하세요.")
		window.$('body').click(() => {
			window.$('body').append("클릭")
		});
	});
</script>

<svelte:head>
	<title>Home</title>
	<meta name="description" content="홈 화면"/>
</svelte:head>

<section class="text-red-500">
	<i class="fa-solid fa-user"></i> 메인입니다.
</section>