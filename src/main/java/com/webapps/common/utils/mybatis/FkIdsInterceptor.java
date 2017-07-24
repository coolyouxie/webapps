package com.webapps.common.utils.mybatis;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.webapps.common.bean.DomainEntityByFkIdsMapper;
import com.webapps.common.bean.DomainEntityRMapper;
import com.webapps.common.bean.FkId;

@Intercepts(value = { @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class FkIdsInterceptor implements Interceptor {

	public Object intercept(Invocation invocation) throws Throwable {

		try {

			resetFkIds(invocation);
			return invocation.proceed();

		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	private void resetFkIds(Invocation invocation) {
		try {

			RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation
					.getTarget();

			Field field = statementHandler.getClass().getDeclaredField(
					"delegate");
			field.setAccessible(true);
			BaseStatementHandler handler = (BaseStatementHandler) field
					.get(statementHandler);
			Field field2 = BaseStatementHandler.class
					.getDeclaredField("mappedStatement");
			field2.setAccessible(true);
			MappedStatement statement = (MappedStatement) field2.get(handler);
			String iface = statement.getId();
			Class clazz = Class.forName(iface.replace("."+
					iface.split("\\.")[iface.split("\\.").length - 1], ""));
			if (!(DomainEntityByFkIdsMapper.class.isAssignableFrom(clazz)
					||
					DomainEntityRMapper.class.isAssignableFrom(clazz))) {
				return;
			}
			FkId params = (FkId) clazz.getAnnotation(FkId.class);
			Map objs = (Map) statementHandler.getParameterHandler()
					.getParameterObject();
			Object[] objss = (Object[]) objs.get("array");
			for (int i = 0; i < params.value().length; i++) {
				objs.put(params.value()[i], objss[i]);
			}
		} catch (Exception ex) {

		}

	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
	}

}
