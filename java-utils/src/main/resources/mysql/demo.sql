SELECT
	id
FROM
	(
	SELECT
		t1.id,
	IF
		 ( find_in_set( p_id, @pids ) > 0, @pids := concat( @pids, ',', id ), 0 ) AS ischild
	FROM
		( SELECT id, p_id FROM sys_admin t ORDER BY p_id, id ) t1,
		( SELECT @pids := 18555972869325 ) t2
		) t3
WHERE
	ischild != 0


	// 递归查询自己ID下面的节点
