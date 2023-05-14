package com.neosoft.userapi.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_address")
public class UserAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "addid", length = 10)
	private Integer addId;
	@Column(name = "addressone", length = 200)
	private String addressOne;
	@Column(name = "addresstwo", length = 200)
	private String addressTwo;

	public Integer getAddId() {
		return addId;
	}

	public void setAddId(Integer addId) {
		this.addId = addId;
	}

	public String getAddressOne() {
		return addressOne;
	}

	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}

	public String getAddressTwo() {
		return addressTwo;
	}

	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}

	public UserAddress(Integer addId, String addressOne, String addressTwo) {
		super();
		this.addId = addId;
		this.addressOne = addressOne;
		this.addressTwo = addressTwo;
	}

	public UserAddress() {
		super();
	}

	@Override
	public int hashCode() {
		return Objects.hash(addId, addressOne, addressTwo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAddress other = (UserAddress) obj;
		return Objects.equals(addId, other.addId) && Objects.equals(addressOne, other.addressOne)
				&& Objects.equals(addressTwo, other.addressTwo);
	}

	@Override
	public String toString() {
		return "UserAddress [addId=" + addId + ", addressOne=" + addressOne + ", addressTwo=" + addressTwo + "]";
	}

}
