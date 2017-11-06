package com.webapps.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.webapps.service.IUserRewardService;

@Component
public class ResetLeftTimesJob {
	
	@Autowired
	private IUserRewardService iUserRewardService;
	
	@Scheduled(cron = "${resetLeftTimesJobCorn}")
	public void resetLeftTimesJob(){
		iUserRewardService.resetLeftTimes();
	}

}
