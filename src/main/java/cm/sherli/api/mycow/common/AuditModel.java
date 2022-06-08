package cm.sherli.api.mycow.common;

import java.security.Timestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
@Getter
@Setter
public abstract class AuditModel {

	@CreatedBy
	@Column(name="created_by", updatable=false)
	private String createdBy;
	
	@CreatedDate
	@Column(name="created_dt", updatable =false)
	private Timestamp createdDt;
	
	@LastModifiedBy
	@Column(name="modified_by")
	private String modifiedBy;
	
	@LastModifiedDate
	@Column(name="modified_dt")
	private Timestamp modifiedDt;


	@Override
	public String toString() {
		return "AuditModel [createdBy=" + createdBy + ", createdDt=" + createdDt + ", modifiedBy=" + modifiedBy
				+ ", modifiedDt=" + modifiedDt + "]";
	}

	
}