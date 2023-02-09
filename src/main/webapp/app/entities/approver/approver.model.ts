import dayjs from 'dayjs/esm';

export interface IApprover {
  id: number;
  approverName?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  approvalSettingId?: number | null;
  departmentId?: number | null;
  companyId?: number | null;
}

export type NewApprover = Omit<IApprover, 'id'> & { id: null };
