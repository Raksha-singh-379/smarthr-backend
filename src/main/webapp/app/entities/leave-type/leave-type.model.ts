import dayjs from 'dayjs/esm';

export interface ILeaveType {
  id: number;
  leaveType?: string | null;
  noOfDays?: number | null;
  hasCarryForward?: boolean | null;
  hasEarned?: boolean | null;
  hasCustomPolicy?: boolean | null;
  companyId?: number | null;
  status?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
}

export type NewLeaveType = Omit<ILeaveType, 'id'> & { id: null };
