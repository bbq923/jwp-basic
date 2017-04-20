$(".qna-comment").on("click", ".answerWrite input[type=submit]", addAnswer);
$(".qna-comment").on("click", ".link-delete-article", deleteAnswer);
$("#modify-article").on("click", isAbleToModifyArticle);

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type : 'post',
    url : '/api/qna/addAnswer',
    data : queryString,
    dataType : 'json',
    error: onError,
    success : onSuccess,
  });
  
  $("#writer").val('');
  $("#contents").val('');
}

function deleteAnswer(e) {
	e.preventDefault();
	
	var url = $(e.target).closest("form").attr("action");
	var data = $(e.target).closest("form").serialize();
	var answer = $(e.target).closest("article");
	var count = $(".qna-comment-count strong");
	
	console.log(url);
	console.log(data);
	
	$.ajax({
		type : 'post',
		url : url,
		data: data
	}).done(function(data, status) {
		if (data.result.status) {
			count.text(parseInt(count.text()) - 1);
			answer.remove();
		} else {
			console.log("failed delete answer in database.");
		}
	}).fail(function(jQueryXhr, status) {
		alert("failed to delete an answer.");
	});
}

function isAbleToModifyArticle(e) { //
	e.preventDefault();
	
	var url = "/qna/modify";
	var writer = $(".article-author-name").text();
	var questionId = $("#question-id").val();
	var data = {
			"writer" : writer,
			"questionId" : questionId
	}
	
	console.log(data);
	
	$.ajax({
		type : 'post',
		url : url,
		data : data,
		dataType : 'json'
	}).done(function(data, status) {
		if (data.result.status) {
			location.href = data.url;
		} else {
			alert(data.result.message);
		}
	}).fail(function(jQueryXhr, status) {
		console.log("failed to modify question");
	})
}

function onSuccess(json, status){
  console.log(json);
  var answer = json.answer;
  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.answerId);
  $(".qna-comment-slipp-articles").prepend(template);
  $(".qna-comment-count strong").text(json.countOfComment);
}

function onError(xhr, status) {
  alert("error");
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};