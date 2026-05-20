package br.com.springboot.tabelafipe.entity;

import br.com.springboot.tabelafipe.dto.BudgetDTO;
import br.com.springboot.tabelafipe.status.Status;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@SequenceGenerator(name = "seq_vehicles" , sequenceName = "seq_vehicles", allocationSize = 1)
public class VehicleEntity implements Serializable{

	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vehicles")
	@Column(name = "VEHICLE_ID")
	private Long id;

	private Instant subscriptionDate;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
	@JoinTable(name="TB_VEHICLE_MODEL",joinColumns =
	@JoinColumn( name = "VEHICLE_ID",referencedColumnName="VEHICLE_ID"),
			inverseJoinColumns = @JoinColumn(name = "MODEL_ID",referencedColumnName="MODEL_ID"))
	private ModelEntity modelEntity;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
	@JoinTable(name="TB_VEHICLE_YEAR",joinColumns =
	@JoinColumn( name = "VEHICLE_ID",referencedColumnName="VEHICLE_ID"),
			inverseJoinColumns = @JoinColumn(name = "YEAR_ID",referencedColumnName="YEAR_ID"))
	private YearEntity yearEntity;
	
	//@Column(unique = true)
	//private String renavam;

	private String color;

	@Column(unique = true)
	private String licensePlate;

	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
	@JoinTable(name="TB_VEHICLE_CHARACTERISTIC",joinColumns =
	@JoinColumn( name = "VEHICLE_ID",referencedColumnName="VEHICLE_ID"),
			inverseJoinColumns = @JoinColumn(name = "CHARACTERISTIC_ID",referencedColumnName="CHARACTERISTIC_ID"))
	private CharacteristicEntity characteristicEntity;

	//private String relay;

	//private boolean activeRelay;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserEntity userEntity;

	@OneToMany
	@JoinTable(name="TB_VEHICLE_BUDGET",joinColumns =
	@JoinColumn( name = "VEHICLE_ID",referencedColumnName="VEHICLE_ID"),
			inverseJoinColumns = @JoinColumn(name = "BUDGET_ID",referencedColumnName="BUDGET_ID"))

	List<BudgetEntity> budgetEntityList;

}
	

	