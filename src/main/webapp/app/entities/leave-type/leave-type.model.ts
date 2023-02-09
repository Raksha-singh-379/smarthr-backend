import dayjs from 'dayjs/esm';

export interface ILeaveType {
  id: number;
  leaveType?: string | null;
  noOfDays?: string | null;
  recordStatus?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  companyId?: number | null;
}

export type NewLeaveType = Omit<ILeaveType, 'id'> & { id: null };
