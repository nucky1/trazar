package ar.edu.unsl.trazar.entity;


import lombok.Data;

import javax.persistence.*;

/**
 * @author demig
 *
 */
@Entity
@Data
public class Usuario {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(unique = true,length = 50,nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;
    
    public int getId() {
    	return id;
    }
	public String getUserName() {
		// TODO Auto-generated method stub
		return userName;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	public void setPassword(String encode) {
		// TODO Auto-generated method stub
		password = encode;
	}
	@OneToOne
    @JoinColumn(name = "local_id")
    private Local local;

	public int getLocal() {
		return local.getId();
	}
	public void setLocal(Local local) {
		this.local = local;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
    
}
