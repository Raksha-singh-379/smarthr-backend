import dayjs from 'dayjs/esm';

export interface IApprovalSetting {
  id: number;
  isSequenceApproval?: boolean | null;
  isSimultaneousApproval?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  companyId?: number | null;
}

export type NewApprovalSetting = Omit<IApprovalSetting, 'id'> & { id: null };
