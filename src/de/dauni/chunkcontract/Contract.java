package de.dauni.chunkcontract;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.dauni.chunkcontract.Serializables.M;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("ContractItem")
public class Contract implements ConfigurationSerializable {
	public Integer id;
	public Date created; 
	public String owner;
	public String subject;
	public Date due;
	public String reward;
	public String reward_type;
	public String partner;
	public Boolean waitingforapproval;
	public Date sended_at;
	public Boolean partner_status;
	public Date partner_status_at;
	public Date report_at;
	public Date closed_at;
	public Integer status;
	public Boolean closed;
	public Boolean reported;
	public Integer type;

	public Contract (Integer id, Date created, String owner, String subject, Date due, String reward, String reward_type, String partner, Boolean waitingforapproval,
			Date sended_at, Boolean partner_status, Date partner_status_at, Date report_at, Date closed_at, Integer status, Boolean closed, Boolean reported, Integer type) {
		this.id = id;
		this.created = created;
		this.owner = owner;
		this.subject = subject;
		this.due = due;
		this.reward = reward;
		this.reward_type = reward_type;
		this.partner = partner;
		this.waitingforapproval = waitingforapproval;
		this.sended_at = sended_at;
		this.partner_status = partner_status;
		this.partner_status_at = partner_status_at;
		this.report_at = report_at;
		this.closed_at = closed_at;
		this.status = status;
		this.closed = closed;
		this.reported = reported;
		this.type = type;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("created", created);
		map.put("owner", owner);
		map.put("subject", subject);
		map.put("due", due);
		map.put("reward", reward);
		map.put("reward_type", reward_type);
		map.put("partner", partner);
		map.put("waitingforapproval", waitingforapproval);
		map.put("sended_at", sended_at);
		map.put("partner_status", partner_status);
		map.put("partner_status_at", partner_status_at);
		map.put("waitingforapproval", waitingforapproval);
		map.put("status", status);
		map.put("report_at", report_at);
		map.put("closed_at", closed_at);
		map.put("reported", reported);
		map.put("closed", closed);
		map.put("type", type);
		return map;
	}

	public static Contract deserialize(Map<String, Object> map) {
		return new Contract((Integer) M.g(map, "id", 0), 
				(Date) M.g(map, "created", new Date()), 
				(String) M.g(map, "owner", ""),
				(String) M.g(map, "subject", ""),
				(Date) M.g(map, "due", null),
				(String) M.g(map, "reward", ""),
				(String) M.g(map, "reward_type", "BYTES"),
				(String) M.g(map, "partner", ""),
				(Boolean) M.g(map, "waitingforapproval", false),
				(Date) M.g(map, "sended_at", null),
				(Boolean) M.g(map, "partner_status", false),
				(Date) M.g(map, "partner_status_at", null),
				(Date) M.g(map, "report_at", null),
				(Date) M.g(map, "closed_at", null),
				(Integer) M.g(map, "status", 0),
				(Boolean) M.g(map, "closed", false),
				(Boolean) M.g(map, "reported", false),
				(Integer) M.g(map, "type", 0));
	}
}