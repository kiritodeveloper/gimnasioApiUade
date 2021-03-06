package models;

import java.util.Date;
import java.util.Vector;

import persistence.CertificadoMedicoAbm;
import persistence.SocioAbm;

public class Socio {
	private int 						documento;
	private String 						nombre;
	private String 						domicilio;
	private String 						telefono;
	private String 						email;
	private Abono 						abono;
	private Inscripcion 				inscripcion;
	private Vector<CertificadoMedico> 	aptosMedicos;
	private boolean 					estado;
	
	public Socio(int documento, String nombre, String domicilio, String telefono, String email, Abono abono,
			Inscripcion inscripcion, Vector<CertificadoMedico> aptosMedicos, boolean estado) {
		super();
		this.setDocumento(documento);
		this.setNombre(nombre);
		this.setDomicilio(domicilio);
		this.setTelefono(telefono);
		this.setEmail(email);
		this.setAbono(abono);
		this.setInscripcion(inscripcion);
		this.setAptosMedicos(aptosMedicos);
		this.setEstado(estado);
	}

	public String getNombre() {
		return nombre;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public int getDocumento() {
		return documento;
	}
	public String getTelefono() {
		return telefono;
	}
	public String getEmail() {
		return email;
	}
	public Abono getAbono() {
		return abono;
	}
	public boolean getEstado() {
		return estado;
	}
	public Inscripcion getInscripcion() {
		return inscripcion;
	}
	public Vector<CertificadoMedico> getAptosMedicos() {
		return aptosMedicos;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public void setDocumento(int documento) {
		this.documento = documento;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAbono(Abono abono) {
		this.abono = abono;
	}
	public void setInscripcion(Inscripcion inscripcion) {
		this.inscripcion = inscripcion;
	}
	public void setAptosMedicos(Vector<CertificadoMedico> aptosMedicos) {
		this.aptosMedicos = aptosMedicos;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	/**
	 * esSocio
	 * 
	 * Validamos que exista un socio en particular a través
	 * del documento de identidad
	 * 
	 * @param documento
	 * @return boolean
	 */
	public boolean esSocio(int documento) {
		return (this.documento == documento);
	}

	/**
	 * conAptoMedicoAlDia
	 * 
	 * Validamos que el Socio tenga el apto médico al día,
	 * recorriendo todos sus certificados guardados, validando
	 * que su estado esté en true y que su fecha de vencimiento
	 * sea anterior al día de la fecha.
	 * 
	 * @return boolean
	 */
	public boolean conAptoMedioAlDia() {
		Date hoy = new Date();
		boolean retVal = false;
		Vector<CertificadoMedico> cm = CertificadoMedicoAbm.getInstancia().certificadosDeUnSocio(this.getDocumento());
		
		for (CertificadoMedico c : cm) {
			if (c.getEstado() && !c.getVencimiento().before(hoy)) {
				retVal = true;
			}
		}
		return retVal;
	}

	/**
	 * conAbonoAlDia
	 *
	 * Validamos que el Socio tenga el abono al día, validando
	 * que su fecha de vencimiento sea anterior al día de la fecha.
	 *
	 * @return boolean
	 */
	public boolean conAbonoAlDia() {
		return (this.abono != null && this.abono.abonoVigente());
	}

	public void insert() {
		SocioAbm.getInstancia().insert(this);
	}
	public void eliminarSocio() {
		SocioAbm.getInstancia().delete(this);
	}
	public void actualizarSocio(int documento, String nombre, String domicilio, String telefono, String email, boolean estado) {
		Socio s = new Socio(documento, nombre, domicilio, telefono, email, abono, inscripcion, aptosMedicos, estado);
		SocioAbm.getInstancia().update(s);
	}
	
}
