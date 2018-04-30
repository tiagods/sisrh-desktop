package com.plkrhone.sisrh.observer;

import com.plkrhone.sisrh.model.*;
import com.plkrhone.sisrh.repository.helper.CandidatosImp;

public interface InterfaceCandidato {
	public void registerCandidato(Candidato c, EnumCandidato e, int change);
	public void unregisterCandidato(Candidato c, EnumCandidato[] e, int change);
	public void notifyUpdate(CandidatosImp candidatos) throws Exception;
	public void clear();
}
