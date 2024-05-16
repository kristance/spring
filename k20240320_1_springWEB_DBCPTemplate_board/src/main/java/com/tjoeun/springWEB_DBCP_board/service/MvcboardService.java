package com.tjoeun.springWEB_DBCP_board.service;

import org.springframework.ui.Model;

import com.tjoeun.springWEB_DBCP_board.vo.MvcboardVO;

public interface MvcboardService {

	public abstract void execute(MvcboardVO mvcboardVO);
	public abstract void execute(Model model);
	
}
