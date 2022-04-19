var actionForm = $("form");

$(".modifyBtn").click(function(){
console.log("여기와?");
if(!confirm("수정하시겠나요?")){
    return;
}
    actionForm
    .attr("action","/board/modify")
    .attr("method","post")
    .submit();
    console.log("여기와?");
});



$(".removeBtn").click(function(){
    actionForm.attr("action","/board/remove").attr("method","post").submit();
});


$(".listBtn").click(function(){

    var page = $("input[name='page']");
    var type = $("input[name='type]");
    var keyword = $("input[name='keyword']");

    actionForm.empty();
    actionForm.append(page);
    actionForm.append(type);
    actionForm.append(keyword);

    actionForm.attr("action","/board/list").attr("method","get").submit();


});

