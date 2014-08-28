package com.kingdee.purchase.openapi.client;

import java.io.Serializable;

public class APIId implements Serializable, Comparable<APIId> {

	private static final long serialVersionUID = 1L;
	public static final int NONE_VERSION = -1;
	public static final int DEFAULT_VERSION = 1;
	private final String namespace;
	private final String name;
	private final int version;

	public APIId(String namespace, String name) {
		this(namespace, name, -1);
	}

	public APIId(String namespace, String name, int version) {
		this.namespace = namespace;
		this.name = name;
		this.version = version;
	}

	public String toString() {
		return this.namespace + ':' + this.name + '-' + this.version;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (!(obj instanceof APIId))) {
			return false;
		}
		APIId other = (APIId) obj;
		if ((this.version == other.version)
				&& (((this.namespace != null) && (this.namespace.equals(other.namespace))) || ((this.namespace == null)
						&& (other.namespace == null) && (((this.name != null) && (this.name.equals(other.name))) || ((this.name == null) && (other.name == null)))))) {
			return true;
		}
		return false;
	}

	public int hashCode() {
		return _hashCode();
	}

	protected int _hashCode() {
		int iTotal = 17;
		if (this.namespace == null) {
			iTotal *= 37;
		} else {
			iTotal = iTotal * 37 + this.namespace.hashCode();
		}
		if (this.name == null) {
			iTotal *= 37;
		} else {
			iTotal = iTotal * 37 + this.name.hashCode();
		}
		return iTotal * 37 + this.version;
	}

	public String getNamespace() {
		return this.namespace;
	}

	public String getName() {
		return this.name;
	}

	public int getVersion() {
		return this.version;
	}

	public int compareTo(APIId o) {
		if (o == null) {
			return 1;
		}
		if (o == this) {
			return 0;
		}
		if (this.namespace != o.namespace) {
			if (this.namespace == null) {
				return -1;
			}
			if (o.namespace == null) {
				return 1;
			}
			int t = this.namespace.compareTo(o.namespace);
			if (t != 0) {
				return t;
			}
		}
		if (this.name != o.name) {
			if (this.name == null) {
				return -1;
			}
			if (o.name == null) {
				return 1;
			}
			int t = this.name.compareTo(o.name);
			if (t != 0) {
				return t;
			}
		}
		if (this.version != o.version) {
			return this.version > o.version ? 1 : -1;
		}
		return 0;
	}
}
