package com.example.demo.service;

import static org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus.APPROVED;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.OAuthMapper;
import com.example.demo.vo.OAuthApproval;

@Service("oauthApprovalStoreService")
public class OAuthApprovalStoreService implements ApprovalStore {

	private static final Logger log = LoggerFactory.getLogger(OAuthTokenStoreService.class);

	@Autowired
	private OAuthMapper mapper;

	private boolean handleRevocationsAsExpiry = false;
	
	public void setHandleRevocationsAsExpiry(boolean handleRevocationsAsExpiry) {
		this.handleRevocationsAsExpiry = handleRevocationsAsExpiry;
	}
	
	@Override
	public boolean addApprovals(Collection<Approval> approvals) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("adding approvals: [%s]", approvals));
		}
		boolean success = true;
		for (Approval approval : approvals) {
			OAuthApproval appr = new OAuthApproval();
			appr.setExpiresAt(approval.getExpiresAt().getTime());
			appr.setStatus((approval.getStatus() == null ? APPROVED : approval.getStatus()).toString());
			appr.setLastModifiedAt(approval.getLastUpdatedAt().getTime());
			appr.setUserId(approval.getUserId());
			appr.setClientId(approval.getClientId());
			appr.setScope(approval.getScope());
			
			if (!refreshApproval(appr)) {
				if (!saveApproval(appr)) {
					success = false;
				}
			}
		}
		return success;
	}

	private boolean refreshApproval(final OAuthApproval approval) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("refreshing approval: [%s]", approval));
		}
		int refreshed = mapper.refreshApproval(approval);
		if (refreshed != 1) {
			return false;
		}
		return true;
	}

	private boolean saveApproval(final OAuthApproval approval) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("refreshing approval: [%s]", approval));
		}
		int refreshed = mapper.saveApproval(approval);
		if (refreshed != 1) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean revokeApprovals(Collection<Approval> approvals) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("Revoking approvals: [%s]", approvals));
		}
		boolean success = true;
		for (final Approval approval : approvals) {
			if (handleRevocationsAsExpiry) {
				OAuthApproval appr = new OAuthApproval();
				appr.setExpiresAt(System.currentTimeMillis());
				appr.setUserId(approval.getUserId());
				appr.setClientId(approval.getClientId());
				appr.setScope(approval.getScope());
				
				int refreshed = mapper.expireApproval(appr);
				if (refreshed != 1) {
					success = false;
				}
			}
			else {
				OAuthApproval appr = new OAuthApproval();
				appr.setUserId(approval.getUserId());
				appr.setClientId(approval.getClientId());
				appr.setScope(approval.getScope());
				
				int refreshed = mapper.deleteApproval(appr);
				if (refreshed != 1) {
					success = false;
				}
			}
		}
		return success;
	}

	@Override
	public Collection<Approval> getApprovals(String userId, String clientId) {
		List<Approval> coll = null;
		List<OAuthApproval> list = mapper.findByUserIdAndClientId(userId, clientId);
		if(list != null && list.size() > 0) {
			coll = new ArrayList<Approval>();
			for(OAuthApproval approval : list) {
				String userId1 = approval.getUserId();
				String clientId1 = approval.getClientId();
				String scope = approval.getScope();
				Date expiresAt = new Date(approval.getExpiresAt().getTime());
				String status = approval.getStatus();
				Date lastUpdatedAt = new Date(approval.getLastModifiedAt().getTime());
				
				coll.add(new Approval(userId1, clientId1, scope, expiresAt, ApprovalStatus.valueOf(status), lastUpdatedAt));
			}
		}
		return coll;
	}
}