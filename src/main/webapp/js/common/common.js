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
	if (type == "city") {
		$("#city").empty();
		$("#city").append("<option value='-1'>-请选择-</option>");
		$("#area").empty();
		$("#area").append("<option value='-1'>-请选择-</option>");
		if ($("#province").val() == -1) {
			return;
		}
	}
	
	if(type == "area") {
		$("#area").empty();
		$("#area").append("<option value='-1'>-请选择-</option>");
		if ($("#city").val() == -1) {
			return;
		}
	}
	
	$.ajax({
		url:contextPath+"/province/queryProvinceByParentId",
		type:"POST",
		dataType:"JSON",
		async:false,
		data:{
			parentId:parentId,
			random:Math.random()
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
	});
}

/**
 * 根据行政区ID加载门店信息
 * @param contextPath
 * @param districtId
 * @param type
 */
function loadAgencyByDistrictId(contextPath,districtId){
    if(districtId==-1){
        return;
    }
    $.ajax({
        url:contextPath+"/agency/loadAgencyByDistrictId",
        type:"POST",
        dataType:"JSON",
        data:{
            areaId:districtId
        },
        success:function(response){
            if(response&&response.result=="S"){
                var agencyList = response.data;
                $("#agency").empty();
                $("#agency").append("<option value='-1'>-请选择-</option>");
                for(var i=0;i<agencyList.length;i++){
                    if($("#curAgencyId").val()==agencyList[i].id){
                        $("#agency").append("<option value='"+agencyList[i].id+"' selected='selected'>"+agencyList[i].name+"</option>");
                    }else{
                        $("#agency").append("<option value='"+agencyList[i].id+"'>"+agencyList[i].name+"</option>");
                    }
                }
            }
        }
    });
}