<script>
	import {toastWarning} from "../../../app.js";

	async function handleSubmit(event) {
		event.preventDefault();
		const formData = new FormData(event.target);

		if (formData) {
			const response = await fetch(`/sbb/question/write`, {
				method: 'POST',
				body: formData,
			});

			if (!response.ok) {
				const errorData = await response.json();
				if (errorData.subject) {
					toastWarning(errorData.subject);
					return;
				}
			}

			window.location.href = `/question`;
		}
	}


</script>

<svelte:head>
	<title>SBB</title>
	<meta name="description" content="SBB Question List" />
</svelte:head>

<section class="pl-10 pr-10">
	<div>
		<h2 class="text-3xl font-bold border-bottom py-2 m-5">질문 작성</h2>
		<form on:submit={handleSubmit} method="post">
			<div class="flex flex-col m-5">
				<label for="subject" class="form-label">제목</label>
				<input class="input input-bordered input-primary mt-3 max-w-full" name="subject" id="subject" type="text" placeholder="제목을 입력해주세요."/>
			</div>
			<div class="flex flex-col m-5">
				<label for="content" class="form-label">내용</label>
				<textarea class="textarea textarea-primary mt-3 resize-none" name="content" id="content" rows="8" placeholder="내용을 입력해주세요."></textarea>
			</div>
			<div class="flex flex-col m-5">
				<button type="submit" class="btn btn-primary mt-3">작성</button>
			</div>
		</form>
	</div>
</section>

<style>

</style>
