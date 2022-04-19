$(document).ready(function(){


    var listGroup = $(".replyList");

    function formatTime(str){
    var date = new Date(str);

    return date.getFullYear() + '/' +
            (date.getMonth()+1) +'/' +
            date.getDate() +' ' +
            date.getHours() + ':' +
            date.getMinutes();
    }

    function loadJSONData(){
        $.getJSON('/replies/board/'+bno, function(arr){

            console.log(arr);
            
            var str ="";

        $(".replyCount").html("Reply Count "+arr.length);

        $.each(arr, function(index,reply){
            console.log(arr);
            console.log(index);
            console.log(reply);
                str += '<div class="card-body" data-rno="'+reply.rno+'"><b>'+reply.rno + '</b>';
                str += '<h5 class="card-title">'+reply.text+'</h5>';
                str += '<h6 class="card-subtitle mb-2 text-muted">'+reply.replier+'</h6>';
                str += '<p class="card-text">'+formatTime(reply.regDate)+'</p>';
                str += '</div>';
        })
        listGroup.html(str);
        });
    }
    $(".replyCount").click(function(){
        loadJSONData();
    })

    $(".addReply").click(function(){
        console.log("여기까지 오냐고 addReply");
        modal.modal('show');

        $('.input[name="replyText"]').val('');
        $('.input[name="replier"]').val('');

        $(".modal-footer .btn").hide();
        $(".replySave, .replyClose").show();
    })

    $(".replySave").click(function(){
        var reply = {
            bno: bno,
            text: $('input[name="replyText"]').val(),
            replier: $('input[name="replier"]').val()
        }
        console.log(reply);

        $.ajax({
            url: '/replies/reply',
            method: 'post',
            data: JSON.stringify(reply),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function(data){
                console.log(data);
     
                var newRno = parseInt(data);
        
                alert(newRno +"번 댓글이 등록되었습니다.");
                modal.modal('hide');
                loadJSONData();
            }
           
        })

    });
    $(".replyRemove").click(function(){

        var rno = $("input[name='rno']").val();


        $.ajax({
            url: '/replies/' + rno,
            method: 'delete',
            success: function(result){
                console.log("result: "+result);
                if(result === 'success'){
                    alert("댓글이 삭제되었습니다.");
                    modal.modal('hide');
                    loadJSONData();
                }
            }
        })


      });
    $('.replyList').on("click",".card-body", function(){
        var white = $(this);
        console.log(white);
        var rno = $(this).data("rno");
        console.log(rno);

        $("input[name='replyText']").val($(this).find('.card-title').html());
        $("input[name='replier']").val($(this).find('.card-subtitle').html());
        $("input[name='rno']").val(rno);

        $(".modal-footer .btn").hide();
        $(".replyRemove, .replyModify, replyClose").show();

        modal.modal('show');

    });

    $(".replyModify").click(function(){

        var rno = $("input[name='rno']").val();

        var reply = {
            rno: rno,
            bno: bno,
            text: $("input[name='replyText']").val(),
            replier: $("input[name='replier']").val()
        }
        console.log(reply);

        $.ajax({
            url:'/replies/' + rno,
            method: 'put',
            data: JSON.stringify(reply),
            contentType: 'application/json; charset=utf-8',
            success:function(result){

                console.log("result:" +result);

                if(result === 'success'){
                    alert("댓글이 수정되었습니다.");
                    modal.modal('hide');
                    loadJSONData();
                }
            }
    })
    })

    });
   



