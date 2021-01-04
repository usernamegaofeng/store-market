package pers.store.market.goods.domain.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="undo_log")
public class Log implements Serializable {

	@Id
	private Long id;//id
	private Long branchId;//branch_id
	private String xid;//xid
	private byte[] rollbackInfo;//rollback_info
	private Integer logStatus;//log_status
	private Date logCreated;//log_created
	private Date logModified;//log_modified
	private String ext;//ext

}
