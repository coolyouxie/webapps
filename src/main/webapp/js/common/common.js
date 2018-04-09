var CONTEXT_PATH = '';
function goBack() {
    window.history.go(-1);
}

function loadDistrict(contextPath,type) {
    var parentId = 0;
    var tmpProvince = $("#tmpProvince").val();
    var tmpCity = $("#tmpCity").val();
    var tmpArea = $("#tmpArea").val();
    if (type != "province") {
        if (type == "city") {
            parentId = $("#province").val();
        } else if (type == "area") {
            parentId = $("#city").val();
        }
    }
    if (type == "city") {
    	  $("#city").empty();
          $("#city").append("<option value='-1'>-请选择-</option>");
          $("#area").empty();
          $("#area").append("<option value='-1'>-请选择-</option>");
        if ($("#province").val() == -1) {
            return;
        }
    }
    if (type == "area") {
    	$("#area").empty();
        $("#area").append("<option value='-1'>-请选择-</option>");
        if ($("#city").val() == -1) {
            return;
        }
    }
    $.ajax({
        url: contextPath+"/province/queryProvinceByParentId",
        type: "POST",
        dataType: "JSON",
        data: {
            parentId: parentId
        },
        success: function (response) {
            if (response && response.result == "S") {
                var provinces = response.data;
                $("#" + type).empty();
                $("#" + type).append("<option value='-1'>-请选择-</option>");
                for (var i = 0; i < provinces.length; i++) {
                    if (tmpProvince == provinces[i].id) {
                        $("#" + type).append("<option value='" + provinces[i].id + "' selected='selected'>" + provinces[i].name + "</option>");
                    } else if (tmpCity == provinces[i].id) {
                        $("#" + type).append("<option value='" + provinces[i].id + "' selected='selected'>" + provinces[i].name + "</option>");
                    } else if (tmpArea == provinces[i].id) {
                        $("#" + type).append("<option value='" + provinces[i].id + "' selected='selected'>" + provinces[i].name + "</option>");
                    } else {
                        $("#" + type).append("<option value='" + provinces[i].id + "'>" + provinces[i].name + "</option>");
                    }
                }
            }
        }
    });
}

function loadDistrictByParentId(contextPath,parentId,type){
	var tmpCity = $("#tmpCity").val();
	var tmpArea = $("#tmpArea").val();
	$.ajax({
		url:contextPath+"/province/queryProvinceByParentId",
		type:"POST",
		dataType:"JSON",
		data:{
			parentId:parentId
		},
		success:function(response){
			if(response&&response.result=="S"){
				var provinces = response.data;
				$("#"+type).empty();
				$("#"+type).append("<option value='-1'>-请选择-</option>");
				for(var i=0;i<provinces.length;i++){
					if(tmpCity==provinces[i].id){
						$("#"+type).append("<option value='"+provinces[i].id+"' selected='selected'>"+provinces[i].name+"</option>");
					}else if(tmpArea==provinces[i].id){
						$("#"+type).append("<option value='"+provinces[i].id+"' selected='selected'>"+provinces[i].name+"</option>");
					}else{
						$("#"+type).append("<option value='"+provinces[i].id+"'>"+provinces[i].name+"</option>");
					}
				}
			}
		}
	})
}